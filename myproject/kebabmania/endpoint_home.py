import random

from django.http import JsonResponse

from kebabmania.models import Usuario, Opiniones, Kebab


def home(request):
    request_token = request.META.get('HTTP_SESSION_TOKEN')

    if request_token is None:
        return JsonResponse({"error": "Unauthorized"}, status=401)
    try:
        user = Usuario.objects.get(token=request_token)
    except Usuario.DoesNotExist:
        return JsonResponse({"error": "Unauthorized"}, status=401)

    if request.method != "GET":
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)


    last_opinion = Opiniones.objects.all().order_by('-id')[:1]


    json_last_opinion = last_opinion.to_json()


    json_response = []

    json_response.append(json_last_opinion)


    all_kebab = Kebab.objects.all()

    random_number = random.randint(0, len(all_kebab))


    random_kebab = Kebab.objects.get(id=random_number)


    json_response.append(random_kebab)

    return JsonResponse(json_response, safe=False)
