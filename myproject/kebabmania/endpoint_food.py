from django.http import JsonResponse

from kebabmania.models import Ciudad, Plato


def allfood(request):
    if request.method != "GET":
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)

    all_rows = Plato.objects.all()

    json_response = []

    for row in all_rows:
        json_response.append(row.to_json())

    return JsonResponse(json_response, safe=False)