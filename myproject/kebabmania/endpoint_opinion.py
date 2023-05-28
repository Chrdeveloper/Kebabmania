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

    user = Usuario.objects.get(token=request_token)
    userjson = user.to_json()
    # Si el verbo es un get entrara aqui
    if request.method == "GET":

        usuario = Usuario.objects.get(telefono=tlf)


        all_rows = Opiniones.object.filter(id_usuario=usuario)

        json_response = []

        for row in all_rows:



            json_row= row.to_json()

            kebab = Kebab.objects.get(id=json_row['id_kebab'])

            kebab_json = kebab.to_json()
            opinion_json = []
            opinion_json['nombre_kebab'] = kebab_json['nombre']

            opinion_json['nota'] = json_row['nota']

            json_response.append(opinion_json)

        return JsonResponse(json_response, safe=False)

    elif request.method == "POST":

        json_body = json.loads(request.body)

        json_nota = json_body['nota']
        json_idKebab = json_body['id_kebab']

        kebab = Kebab.objects.get(id=json_idKebab)

        existingOpinion = Opiniones.objects.filter(id_kebab=kebab, id_usuario=user)

        if existingOpinion is not None:
            Opiniones.objects.filter(id_kebab=kebab, id_usuario=user).delete()

        new_opinion = Opiniones(id_usuario=user,id_kebab=kebab,nota = json_nota)

        new_opinion.save()


        return JsonResponse({'created': 'True'}, status=201)

    else:
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)