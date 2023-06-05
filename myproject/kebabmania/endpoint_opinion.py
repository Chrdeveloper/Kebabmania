import json

from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt

from kebabmania.models import Usuario, Opiniones, Kebab

@csrf_exempt
def opinions (request, tlf):
    request_token = request.META.get('HTTP_SESSION_TOKEN')

    if request_token is None:
        return JsonResponse({"error": "Unauthorized"}, status=401)
    try:
        user = Usuario.objects.get(token=request_token)
    except Usuario.DoesNotExist:
        return JsonResponse({"error": "Unauthorized"}, status=401)


    # Si el verbo es un get entrara aqui y recibira todas las opiniones de un usuario en concreto
    if request.method == "GET":

        usuario = Usuario.objects.get(telefono=tlf)


        all_rows = Opiniones.objects.filter(id_usuario=usuario)

        json_response = []

        for row in all_rows:



            json_row= row.to_json()

            kebab = Kebab.objects.get(id=json_row['id_kebab'])

            kebab_json = kebab.to_json()
            opinion_json = {'nombre_kebab':kebab_json['nombre'], 'nota':str (json_row['nota']) }

            json_response.append(opinion_json)

        return JsonResponse(json_response, safe=False)
    #Enviara una peticion post para crear una nueva opinion
    elif request.method == "POST":
        usuario = Usuario.objects.get(telefono=tlf)
        json_body = json.loads(request.body)

        json_nota = json_body['nota']
        json_idKebab = json_body['id_kebab']

        if json_nota is None or json_idKebab is None:
            return JsonResponse({'error': 'Missing parameters'}, status=400)

        kebab = Kebab.objects.get(id=json_idKebab)

        existingOpinion = Opiniones.objects.filter(id_kebab=kebab, id_usuario=usuario)

        if existingOpinion is not None:
            Opiniones.objects.filter(id_kebab=kebab, id_usuario=usuario).delete()

        new_opinion = Opiniones(id_usuario=user,id_kebab=kebab,nota=json_nota)

        new_opinion.save()


        return JsonResponse({'created': 'True'}, status=201)

    else:
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)





